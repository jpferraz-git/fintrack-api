import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsTable } from './alerts-table.component';

const meta: Meta<AlertsTable> = {
  title: 'Components/AlertsTable',
  component: AlertsTable,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsTable>;

export const Primary: Story = {
  args: {},
};
