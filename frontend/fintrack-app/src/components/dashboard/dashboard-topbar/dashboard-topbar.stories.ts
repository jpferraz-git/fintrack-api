import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardTopbar } from './dashboard-topbar.component';

const meta: Meta<DashboardTopbar> = {
  title: 'Components/DashboardTopbar',
  component: DashboardTopbar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardTopbar>;

export const Primary: Story = {
  args: {},
};
