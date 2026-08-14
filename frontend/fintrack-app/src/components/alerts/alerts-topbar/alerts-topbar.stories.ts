import type { Meta, StoryObj } from '@storybook/angular';
import { AlertsTopbar } from './alerts-topbar.component';

const meta: Meta<AlertsTopbar> = {
  title: 'Components/AlertsTopbar',
  component: AlertsTopbar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AlertsTopbar>;

export const Primary: Story = {
  args: {},
};
