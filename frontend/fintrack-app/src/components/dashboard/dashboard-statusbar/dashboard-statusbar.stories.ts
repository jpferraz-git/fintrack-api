import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardStatusbar } from './dashboard-statusbar.component';

const meta: Meta<DashboardStatusbar> = {
  title: 'Components/DashboardStatusbar',
  component: DashboardStatusbar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardStatusbar>;

export const Primary: Story = {
  args: {},
};
